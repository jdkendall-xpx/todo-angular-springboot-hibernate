import {Injectable} from '@angular/core';
import {TodoEntry} from '../domain/todoEntry';

@Injectable({
  providedIn: 'root'
})
export class TodoApiService {

  private todos: TodoEntry[] = [];

  constructor() {
  }

  async createTodo(todoEntry: TodoEntry): Promise<boolean> {
    this.todos.push(todoEntry);
    return true;
  }

  async getTodoList(): Promise<TodoEntry[]> {
    return this.todos;
  }

  async getTodoEntry(entryId: number): Promise<TodoEntry> {
    const entry = this.todos.find(todo => todo.id === entryId);
    return entry || Promise.reject('Entry not found');
  }

  async deleteTodo(entryId: number): Promise<void> {
    if (!this.todos.some(todo => todo.id === entryId)) {
      return Promise.reject('Entry not found');
    }

    this.todos = this.todos.filter(todo => todo.id !== entryId);
    return;
  }
}
