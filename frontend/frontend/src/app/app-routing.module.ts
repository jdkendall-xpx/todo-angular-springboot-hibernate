import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {EditTodoDialogComponent} from './pages/home/widgets/edit-todo-dialog/edit-todo-dialog.component';
import {CreateTodoDialogComponent} from './pages/home/widgets/create-todo-dialog/create-todo-dialog.component';

const routes: Routes = [
  {path: 'create', component: CreateTodoDialogComponent},
  {path: 'edit/:id', component: EditTodoDialogComponent},
  {path: '', component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
