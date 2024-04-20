import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../Services/project.service';
import { Project } from '../../Types/Project';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project-component.html',
  styleUrl: './edit-project-component.css',
})
export class EditProjectComponent implements OnInit {
  protected project: Project;
  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute
  ) {
    this.project = this.projectService.getEmptyProject();
  }
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      if (params.get('projectId') === 'new') {
        this.project = this.projectService.getEmptyProject();
      } else {
        this.projectService
          .getProject(<string>params.get('projectId'))
          .subscribe((project) => (this.project = project));
      }
    });
  }
  protected submitForm(even: any) {
    console.log(even)
  }
}
