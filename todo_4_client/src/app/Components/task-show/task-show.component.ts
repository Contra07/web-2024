import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Task } from '../../Types/Task';

@Component({
  selector: 'app-task-show',
  templateUrl: './task-show.component.html',
  styleUrl: './task-show.component.css',
})
export class TaskShowComponent implements OnInit {
  @Output() public deleteTaskEvent: EventEmitter<string> =
    new EventEmitter<string>();
  @Input() public task: Task | null = null;
  constructor() {}
  ngOnInit(): void {
  }
  isTaskExpired():boolean{
    if (this.task !== null) {
      const now = new Date();
      const date = new Date(this.task.completionDate);
      return date < now && !this.task.done;
    }
    return false
  }
  raiseDeleteTaskEvent() {
    this.deleteTaskEvent.emit(this.task?.id);
  }
}
