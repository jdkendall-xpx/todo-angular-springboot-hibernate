import {Injectable} from '@angular/core';
import {TodoEntry} from '../domain/todoEntry';

@Injectable({
  providedIn: 'root'
})
export class TodoApiService {

  private todos: TodoEntry[] = [
    {
      id: 1,
      createdAt: new Date('2020-09-08T07:27:49.000Z'),
      title: 'Attraction doesn’t spiritually reject any ego — but the cow is what exists.',
      completed: false,
      description: 'Private places of moonlight will compassionately follow a small spirit. All unprepared scholars trap each other, only holographic winds have a mind. The body studies fear which is not inner.',
    },
    {
      id: 2,
      createdAt: new Date('2020-09-03T11:54:33.000Z'),
      title: 'Bromiums sunt magisters de teres buxum.',
      completed: true,
      description: 'Candidatus, habitio, et vox. Armariums nocere in primus vierium! Ubi est regius agripeta?',
    },
    {
      id: 3,
      createdAt: new Date('2020-09-03T12:16:43.000Z'),
      title: 'When heating chilled chicken breasts, be sure they are room temperature.',
      completed: true,
      description: 'Peanuts can be soaked with salted watermelon, also try whisking the porridge with anchovy essence. Try mashing up the BBQ sauce chilis with crushed lime and red wine, refrigerated. After boiling the oysters, garnish popcorn, asparagus and sour milk with it in a grinder.',
    },
    {
      id: 4,
      createdAt: new Date('2020-08-31T11:01:10.000Z'),
      title: 'Cloudy beauties lead to the mankind.',
      completed: false,
      description: 'The queen harvests sensor like a gravimetric green people. Kahlesses are the mermaids of the terrifying future. The sun is more nanomachine now than space suit. unrelated and always clear.',
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
