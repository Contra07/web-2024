import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../Services/project.service';
import { Project } from '../../Types/Project';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-project-list-component',
  templateUrl: './project-list-component.html',
  styleUrl: './project-list-component.css',
})
export class ProjectListComponent implements OnInit {
  protected projectsToShow: Project[] = [];
  protected projects: Project[] = [];
  protected taskNumber: Map<string, number> = new Map<string, number>();
  constructor(private projectService: ProjectService) {}
  ngOnInit(): void {
    forkJoin([
      this.projectService.getProjects(),
      this.projectService.getProjectUnfinishedTasksNumber(),
    ]).subscribe(
      (result) => {
        this.projects = result[0];
        this.taskNumber = result[1];
        this.projectsToShow = this.projects;
      }
    );
  }
  onEnter(value: string): void {
    if (value === null || value.match(/^ *$/) !== null){
      this.projectsToShow = this.projects;
    }
    else{
      this.projectsToShow = this.projects.filter((project) => {
        return (
          project.name.toUpperCase().includes(value.toUpperCase()) ||
          project.description.toUpperCase().includes(value.toUpperCase())
        );
      });
    }
  }
}
