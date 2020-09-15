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

  updateTodoEntry(entry: TodoModelState): void {
    const updated = replaceFirst(this.todosSubject.getValue(), entry, ifIdsMatch);
    this.todosSubject.next(updated);
  }

  toggleTodoCompletedState(todo: TodoModelState): void {
    // Ignore attempts to change a dirty/pending component
    if (todo.dirty) {
      return;
    }

    const updated: TodoModelState = {...todo, completed: !todo.completed, dirty: true, error: false};
    this.updateTodoEntry(updated);

    this.todoApi.updateTodo(updated as TodoEntry)
      .pipe(
        // For good values, map response to the model & update our current entry with fresh state
        map(n => n as TodoModelState),
        tap((value) => {
          this.updateTodoEntry({...value, dirty: false, error: false});
        }),
        // For error values, rewind to old to-do entry value and set error state
        catchError((err) => {
          this.updateTodoEntry({...todo, dirty: false, error: true});
          return throwError(err);
        })
      )
      .subscribe();
  }
}

function ifIdsMatch(curObj: TodoModelState, newObj: TodoModelState): boolean {
  return curObj.id === newObj.id;
}
