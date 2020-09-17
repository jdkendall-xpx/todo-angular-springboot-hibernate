import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoApiService} from '../../../../services/todo-api.service';
import {catchError, map, tap} from 'rxjs/operators';
import {replaceFirst} from '../../../../common/util/utilities';

export interface TodoModelState extends TodoEntry {
  dirty?: boolean;
  error?: boolean;
}

export class TodoListModel {
  private readonly todosSubject: BehaviorSubject<TodoModelState[]> = new BehaviorSubject<TodoModelState[]>([]);
  public readonly $todos: Observable<TodoModelState[]> = this.todosSubject.asObservable();

  constructor(private todoApi: TodoApiService) {
  }

  updateTodoList(): void {
    this.todoApi.getTodoList()
      .pipe(
        map((result) => result.map(n => ({...n, dirty: false}))),
        tap((result) => this.todosSubject.next(result))
      ).subscribe();
  }

  updateTodoEntry(entry: TodoEntry): void {
    this.updateInternalTodoEntry({...entry, dirty: false, error: false});
    this.todoApi.updateTodo(entry)
      .pipe(
        catchError((err) => {
          this.updateInternalTodoEntry({...entry, dirty: false, error: true});
          return throwError(err);
        })
      )
      .subscribe();
  }

  addTodoEntry(newEntry: TodoEntry): void {
    const updated = [{...newEntry, dirty: false, error: false} as TodoModelState, ...this.todosSubject.getValue()];
    this.todosSubject.next(updated);

    this.todoApi.createTodo(newEntry)
      .pipe(
        catchError((err) => {
          this.updateInternalTodoEntry({...newEntry, dirty: false, error: true});
          return throwError(err);
        })
      )
      .subscribe();
  }

  toggleTodoCompletedState(todo: TodoModelState): void {
    // Ignore attempts to change a dirty/pending component
    if (todo.dirty) {
      return;
    }

    const updated: TodoModelState = {...todo, completed: !todo.completed, dirty: true, error: false};
    this.updateInternalTodoEntry(updated);

    this.todoApi.updateTodo(updated as TodoEntry)
      .pipe(
        // For good values, map response to the model & update our current entry with fresh state
        map(n => n as TodoModelState),
        tap((value) => {
          this.updateInternalTodoEntry({...value, dirty: false, error: false});
        }),
        // For error values, rewind to old to-do entry value and set error state
        catchError((err) => {
          this.updateInternalTodoEntry({...todo, dirty: false, error: true});
          return throwError(err);
        })
      )
      .subscribe();
  }

  private updateInternalTodoEntry(entry: TodoModelState): void {
    const updated = replaceFirst(this.todosSubject.getValue(), entry, ifIdsMatch);
    this.todosSubject.next(updated);
  }
}

function ifIdsMatch(curObj: TodoModelState, newObj: TodoModelState): boolean {
  return curObj.id === newObj.id;
}
