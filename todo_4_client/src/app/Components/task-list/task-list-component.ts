import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../Services/task.service';
import { Task } from '../../Types/Task';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-task-list-component',
  templateUrl: './task-list-component.html',
  styleUrl: './task-list-component.css',
})
export class TaskListComponent implements OnInit {
  protected projectId: string | null = null;
  protected tasks: Task[] = [];
  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.projectId = params.get('projectId');
      this.getAllTasks();
    });
  }
  getAllTasks() {
    if (this.projectId !== null) {
      this.taskService
        .getTasks(this.projectId)
        .subscribe((tasks) => (this.tasks = tasks));
    }
  }
  deleteTask(id: string) {
    if (this.projectId !== null) {
      this.taskService.deleteTask(this.projectId, id).subscribe((tasks) => {
        this.getAllTasks();
      });
    }
  }
}
