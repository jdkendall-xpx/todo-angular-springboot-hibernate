import {Injectable} from '@angular/core';
import {TodoEntry} from '../domain/todoEntry';

@Injectable({
  providedIn: 'root'
})
export class TodoApiService {

  private todos: TodoEntry[] = [
    {completed: false, createdAt: new Date('2020-09-08T07:27:49.000Z'), description: 'Here be A', id: 1, title: 'A'},
    {completed: true, createdAt: new Date('2020-09-03T11:54:33.000Z'), description: 'Thar be B', id: 2, title: 'B'},
    {completed: true, createdAt: new Date('2020-09-03T12:16:43.000Z'), description: 'Whar be C?', id: 3, title: 'C'},
    {
      completed: false,
      createdAt: new Date('2020-08-31T11:01:10.000Z'),
      description: 'Hardy har dee D!',
      id: 4,
      title: 'D'
    },
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

  async updateTodo(updatedEntry: TodoEntry): Promise<TodoEntry> {
    const index = this.todos.findIndex(entry => entry.id === updatedEntry.id);
    console.log('index found was ' + index);
    this.todos[index] = updatedEntry;
    return updatedEntry;
  }
}
