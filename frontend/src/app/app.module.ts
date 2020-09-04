import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {CreateTodoComponent} from './pages/create-todo/create-todo.component';
import {EditTodoComponent} from './pages/edit-todo/edit-todo.component';
import {TodoApiService} from './services/todo-api.service';
import {TodoListComponent} from './pages/home/widgets/todo-list/todo-list.component';
import {TodoEditorComponent} from './pages/edit-todo/widgets/todo-editor/todo-editor.component';
import {TodoWriterComponent} from './pages/create-todo/widgets/todo-writer/todo-writer.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CreateTodoComponent,
    EditTodoComponent,
    TodoListComponent,
    TodoEditorComponent,
    TodoWriterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [TodoApiService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
