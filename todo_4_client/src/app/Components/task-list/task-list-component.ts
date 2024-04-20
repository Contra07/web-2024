import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../Services/task.service';
import { Task } from '../../Types/Task';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-task-list-component',
  templateUrl: './task-list-component.html',
  styleUrl: './task-list-component.css',
})
export class TaskListComponent implements OnInit {
  protected tasks: Task[] = [];
  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute,
  ) {}
  ngOnInit(): void {
    this.route.paramMap.subscribe(
      (params) => {

        this.taskService
          .getTasks(<string>params.get('projectId'))
          .subscribe((tasks) => (this.tasks = tasks));
      }
    );
  }
}
