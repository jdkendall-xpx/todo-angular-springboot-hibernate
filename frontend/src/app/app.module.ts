import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {CreateTodoDialogComponent} from './pages/home/widgets/create-todo-dialog/create-todo-dialog.component';
import {EditTodoDialogComponent} from './pages/home/widgets/edit-todo-dialog/edit-todo-dialog.component';
import {TodoApiService} from './services/todo-api.service';
import {TodoListComponent} from './pages/home/widgets/todo-list/todo-list.component';
import {TodoEditorComponent} from './pages/home/widgets/edit-todo-dialog/widgets/todo-editor/todo-editor.component';
import {TodoWriterComponent} from './pages/home/widgets/create-todo-dialog/widgets/todo-writer/todo-writer.component';
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
import {HttpClientModule} from '@angular/common/http';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule} from '@angular/material/dialog';
import {ReactiveFormsModule} from '@angular/forms';
import {MatGridListModule} from '@angular/material/grid-list';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CreateTodoDialogComponent,
    EditTodoDialogComponent,
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
    FlexModule,
    HttpClientModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatGridListModule
  ],
  providers: [TodoApiService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
