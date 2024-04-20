import { Component, Input, OnInit } from '@angular/core';
import { Project } from '../../Types/Project';

@Component({
  selector: 'app-project-show',
  templateUrl: './project-show.component.html',
  styleUrl: './project-show.component.css',
})
export class ProjectShowComponent implements OnInit {
  @Input() project: Project | null;
  @Input() taskNumber: number | undefined;
  constructor() {
    this.project = null;
    this.taskNumber = 0;
  }
  ngOnInit(): void {}
}
