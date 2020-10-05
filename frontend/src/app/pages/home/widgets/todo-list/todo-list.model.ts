import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoApiService} from '../../../../services/todo-api.service';
import {catchError, map, tap} from 'rxjs/operators';
import {replaceFirst} from '../../../../common/util/utilities';
import {MatSnackBar} from '@angular/material/snack-bar';
import {v4 as uuid} from 'uuid';

export interface TodoModelState extends TodoEntry {
  checkboxDirty?: boolean;
  checkboxError?: boolean;
  postDirty?: boolean;
  postError?: boolean;
  newPostDirty?: boolean;
  newPostError?: boolean;
}

type TodoModelStateComparator = (obj: TodoModelState, newObj: TodoModelState) => boolean;

export class TodoListModel {
  private readonly todosSubject: BehaviorSubject<TodoModelState[]> = new BehaviorSubject<TodoModelState[]>([]);
  public readonly $todos: Observable<TodoModelState[]> = this.todosSubject.asObservable();

  constructor(private todoApi: TodoApiService, private snackBar: MatSnackBar) {
  }

  updateTodoList(): void {
    this.todoApi.getTodoList()
      .pipe(
        map((result) => result.map(n => n as TodoModelState)),
        tap((result) => this.todosSubject.next(result))
      ).subscribe();
  }

  updateTodoEntry(entry: TodoEntry): void {
    this.updateInternalTodoEntry({...entry, postDirty: true});
    this.todoApi.updateTodo(entry)
      .pipe(
        tap((e) => {
          this.updateInternalTodoEntry(e);
          this.snackBar.open(`Successfully updated to-do entry ${e.id}`, 'OK', {
            duration: 5000,
            panelClass: 'app-snackbar-success'
          });
        }),
        catchError((err) => {
          this.updateInternalTodoEntry({...entry, postError: true});
          this.snackBar.open(`Error updating to-do entry ${entry.id}`, 'OK', {
            duration: 10000,
            panelClass: 'app-snackbar-error'
          });
          return throwError(err);
        })
      )
      .subscribe();
  }

  addTodoEntry(newEntry: TodoEntry): void {
    const tempId = uuid();
    const updated = [{...newEntry, newPostDirty: true, id: tempId} as TodoModelState, ...this.todosSubject.getValue()];
    this.todosSubject.next(updated);

    this.todoApi.createTodo(newEntry)
      .pipe(
        tap((e) => {
          this.updateInternalTodoEntry(e, (c, _) => c.id === tempId);
          this.snackBar.open(`Successfully created to-do entry ${e.id}`, 'OK', {
            duration: 5000,
            panelClass: 'app-snackbar-success'
          });
        }),
        catchError((err) => {
          this.updateInternalTodoEntry({...newEntry, newPostError: true});
          this.snackBar.open(`Error creating new to-do entry`, 'OK', {
            duration: 10000,
            panelClass: 'app-snackbar-error'
          });
          return throwError(err);
        })
      )
      .subscribe();
  }

  toggleTodoCompletedState(todo: TodoModelState): void {
    // Ignore attempts to change a dirty/pending component
    if (todo.checkboxDirty) {
      return;
    }

    const updated: TodoModelState = {...todo, completed: !todo.completed, checkboxDirty: true};
    this.updateInternalTodoEntry(updated);

    this.todoApi.updateTodo(updated as TodoEntry)
      .pipe(
        // For good values, map response to the model & update our current entry with fresh state
        map(n => n as TodoModelState),
        tap((value) => {
          this.snackBar.open(`Successfully updated to-do entry ${value.id}`, 'OK', {
            duration: 5000,
            panelClass: 'app-snackbar-success'
          });
          this.updateInternalTodoEntry(value);
        }),
        // For error values, rewind to old to-do entry value and set error state
        catchError((err) => {
          this.updateInternalTodoEntry({...todo, checkboxError: true});
          this.snackBar.open(`Error toggling completion on to-do entry ${todo.id}`, 'OK', {
            duration: 10000,
            panelClass: 'app-snackbar-error'
          });
          return throwError(err);
        })
      )
      .subscribe();
  }

  deleteTodoEntry(entry: TodoModelState): void {
    this.updateInternalTodoEntry({...entry, postDirty: true});
    this.todoApi.deleteTodo(entry.id)
      .pipe(
        tap(() => {
          this.deleteInternalTodoEntry(entry);
          this.snackBar.open(`Successfully deleted to-do entry ${entry.id}`, 'OK', {
            duration: 5000,
            panelClass: 'app-snackbar-success'
          });
        }),
        catchError((err) => {
          this.updateInternalTodoEntry({...entry, postError: true});
          this.snackBar.open(`Error deleting to-do entry ${entry.id}`, 'OK', {
            duration: 10000,
            panelClass: 'app-snackbar-error'
          });
          return throwError(err);
        })
      ).subscribe();
  }

  private updateInternalTodoEntry(entry: TodoModelState, predicate: TodoModelStateComparator = ifIdsMatch): void {
    const toUpdate = {
      checkboxDirty: false,
      checkboxError: false,
      postDirty: false,
      postError: false,
      newPostDirty: false,
      newPostError: false,
      ...entry
    };
    const updated = replaceFirst(this.todosSubject.getValue(), toUpdate, predicate);
    this.todosSubject.next(updated);
  }

  private deleteInternalTodoEntry(entry: TodoModelState): void {
    const filtered = this.todosSubject.getValue().filter(e => e.id !== entry.id);
    this.todosSubject.next(filtered);
  }
}

function ifIdsMatch(curObj: TodoModelState, newObj: TodoModelState): boolean {
  return curObj.id === newObj.id;
}
