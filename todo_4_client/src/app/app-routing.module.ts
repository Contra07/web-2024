import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectListComponent } from './Components/project-list/project-list-component';
import { EditProjectComponent } from './Components/edit-project/edit-project-component';
import { TaskListComponent } from './Components/task-list/task-list-component';

const routes: Routes = [
  { path: 'projects', component: ProjectListComponent },
  { path: 'projects/:projectId', component: EditProjectComponent },
  { path: 'projects/new', component: EditProjectComponent },
  { path: 'tasks/:projectId', component: TaskListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
