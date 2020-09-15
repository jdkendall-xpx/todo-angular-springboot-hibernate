import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {EditTodoComponent} from './pages/edit-todo/edit-todo.component';
import {CreateTodoComponent} from './pages/home/widgets/create-todo-dialog/create-todo.component';

const routes: Routes = [
  {path: 'create', component: CreateTodoComponent},
  {path: 'edit/:id', component: EditTodoComponent},
  {path: '', component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
