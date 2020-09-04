import {Injectable} from '@angular/core';
import {TodoEntry} from '../domain/todoEntry';

@Injectable({
  providedIn: 'root'
})
export class TodoApiService {

  private todos: TodoEntry[] = [
    {completed: false, createdAt: '', description: 'Here be A', id: 1, title: 'A'},
    {completed: true, createdAt: '', description: 'Thar be B', id: 1, title: 'B'},
    {completed: true, createdAt: '', description: 'Whar be C?', id: 1, title: 'C'},
    {completed: false, createdAt: '', description: 'Hardy har dee D!', id: 1, title: 'D'},
  ];

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
