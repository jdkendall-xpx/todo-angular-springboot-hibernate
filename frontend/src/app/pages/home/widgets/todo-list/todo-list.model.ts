import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoApiService} from '../../../../services/todo-api.service';
import {catchError, map, tap} from 'rxjs/operators';

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
    if (todo.dirty) {
      return;
    }

    const updated: TodoModelState = {...todo, completed: !todo.completed, dirty: true, error: false};
    this.updateTodoEntry(updated);

    this.todoApi.updateTodo(updated as TodoEntry)
      .pipe(
        map(n => n as TodoModelState),
        catchError((err) => {
          this.updateTodoEntry({...todo, dirty: false, error: true});
          return throwError(err);
        }),
        tap((value) => {
          this.updateTodoEntry({...value, dirty: false, error: false});
        })
      )
      .subscribe();
  }
}

function ifIdsMatch(curObj: TodoModelState, newObj: TodoModelState): boolean {
  return curObj.id === newObj.id;
}

function replaceFirst<T>(current: T[], newObj: T, compare: (e: T, n: T) => boolean): T[] {
  const indexToUpdate = current.findIndex(obj => compare(newObj, obj));
  const updated = [...current];
  updated[indexToUpdate] = newObj;
  return updated;
}
