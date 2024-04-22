import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../Services/project.service';
import { Project } from '../../Types/Project';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project-component.html',
  styleUrl: './edit-project-component.css',
})
export class EditProjectComponent implements OnInit {
  protected isError: boolean = false;
  protected isNewProject: boolean = true;
  protected project: Project;
  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.project = this.projectService.getEmptyProject();
  }
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.isNewProject = params.get('projectId') === 'new';
      if (this.isNewProject) {
        this.project = this.projectService.getEmptyProject();
      } else {
        this.projectService
          .getProject(<string>params.get('projectId'))
          .subscribe((project) => (this.project = project));
      }
    });
  }
  protected onSubmit() {
    if (this.isNewProject) {
      this.projectService.addProject(this.project).subscribe((result) => {
        this.isError = result === undefined;
        if (!this.isError) {
          this.router.navigate(['projects']);
        }
      });
    } else {
      this.projectService.updateProject(this.project).subscribe(
        (result) => {
          this.isError = result === undefined;
          if(!this.isError){
            this.router.navigate(['projects']);
          }
        }
      )
    }
  }
}
