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
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {ToggleCheckboxComponent} from './common/components/toggle-checkbox/toggle-checkbox.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatListModule} from '@angular/material/list';
import {TodoCardComponent} from './pages/home/widgets/todo-list/todo-card/todo-card.component';
import {FlexModule} from '@angular/flex-layout';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CreateTodoComponent,
    EditTodoComponent,
    TodoListComponent,
    TodoEditorComponent,
    TodoWriterComponent,
    ToggleCheckboxComponent,
    TodoCardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatListModule,
    FlexModule
  ],
  providers: [TodoApiService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
