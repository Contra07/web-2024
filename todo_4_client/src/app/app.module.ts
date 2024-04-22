import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProjectListComponent } from './Components/project-list/project-list-component';
import { EditProjectComponent } from './Components/edit-project/edit-project-component';
import { TaskListComponent } from './Components/task-list/task-list-component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProjectShowComponent } from './Components/project-show/project-show.component';
import { FormsModule } from '@angular/forms';
import { TaskShowComponent } from './Components/task-show/task-show.component';
@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    EditProjectComponent,
    TaskListComponent,
    ProjectShowComponent,
    TaskShowComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
