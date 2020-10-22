import {Injectable} from '@angular/core';
import {TodoEntry} from '../domain/todoEntry';
import {environment as env} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodoApiService {
  api: string = env.todoApi;

  constructor(private http: HttpClient) {
  }

  createTodo(todoEntry: TodoEntry): Observable<TodoEntry> {
    return this.http.post<ApiTodoEntry>(`${this.api}/todos`, MAPPINGS.TODO_ENTRY.toApi(todoEntry))
      .pipe(map(MAPPINGS.TODO_ENTRY.toDomain));
  }

  getTodoList(): Observable<TodoEntry[]> {
    return this.http.get<ApiTodoEntry[]>(`${this.api}/todos`)
      .pipe(map(e => e.map(MAPPINGS.TODO_ENTRY.toDomain)));
  }

  getTodoEntry(id: number): Observable<TodoEntry> {
    return this.http.get<ApiTodoEntry>(`${this.api}/todos/${id}`)
      .pipe(map(MAPPINGS.TODO_ENTRY.toDomain));
  }

  deleteTodo(id: number): Observable<any> {
    return this.http.delete(`${this.api}/todos/${id}`);
  }

  updateTodo(updatedEntry: TodoEntry): Observable<TodoEntry> {
    const id = updatedEntry.id;
    return this.http.put<ApiTodoEntry>(`${this.api}/todos/${id}`, MAPPINGS.TODO_ENTRY.toApi(updatedEntry))
      .pipe(map(MAPPINGS.TODO_ENTRY.toDomain));
  }
}

interface ApiTodoEntry {
  id: number;
  title: string;
  description: string;
  createdAt: string;
  completed: boolean;
  lastModified?: string;
  completedOn?: string;
  dueOn?: string;
}

const MAPPINGS = {
  TODO_ENTRY: {
    toDomain: (e: ApiTodoEntry): TodoEntry => {
      return {
        ...e,
       createdAt: e.createdAt ? new Date(e.createdAt) : undefined,
       lastModified: e.lastModified ? new Date(e.lastModified) : undefined,
       completedOn: e.completedOn ? new Date(e.completedOn) : undefined,
       dueOn: e.dueOn ? new Date(e.dueOn) : undefined,
      };
    },
    toApi: (e: TodoEntry): ApiTodoEntry => {
    return {
    ...e,
      createdAt: e.createdAt.toISOString(),
      lastModified: e.lastModified ? e.lastModified.toISOString() : undefined,
      completedOn: e.completedOn ? e.completedOn.toISOString() : undefined,
      dueOn: e.dueOn ? e.dueOn.toISOString() : undefined,
    };
    }
    }
};

