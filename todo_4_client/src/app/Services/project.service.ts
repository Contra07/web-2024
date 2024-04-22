import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, map, of } from 'rxjs';
import { Project } from '../Types/Project';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectUrl = `${environment.apiUrl}/projects`;
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };
  constructor(private http: HttpClient) { }

  public getProjects(): Observable<Project[]> {
    return this.http
      .get<Project[]>(this.projectUrl + '/all')
      .pipe(catchError(this.handleError<Project[]>('getProjects', [])))
      // .pipe(map(
      //   (data)=>{data.forEach(element => {
      //     element = this.projectFromProject(element)
      //   })
      //   return data;
      //   }
      // )
      // );
  }

  public getProject(id: string): Observable<Project> {
    const url = `${this.projectUrl}/${id}`;
    return this.http
      .get<Project>(url)
      .pipe(catchError(this.handleError<Project>('getProject', undefined)))
      // .pipe(map(this.projectFromProject));
  }

  public updateProject(project: Project): Observable<Project> {
    const url = `${this.projectUrl}/${project.id}`;
    return this.http
      .put<Project>(url, project, this.httpOptions)
      .pipe(catchError(this.handleError<Project>('updateProject', undefined)))
      // .pipe(map(this.projectFromProject));;
  }

  public addProject(project: Project): Observable<Project> {
    return this.http
      .post<Project>(this.projectUrl, project, this.httpOptions)
      .pipe(catchError(this.handleError<Project>('addProject', undefined)))
      // .pipe(map(this.projectFromProject));;
  }

  public deleteProject(id: number): Observable<Project> {
    const url = `${this.projectUrl}/${id}`;
    return this.http
      .delete<Project>(url, this.httpOptions)
      .pipe(catchError(this.handleError<Project>('deleteProject', undefined)))
      // .pipe(map(this.projectFromProject));
  }

  public getProjectUnfinishedTasksNumber(): Observable<Map<string, number>> {
    const url = `${this.projectUrl}/tasksnumber`;
    return this.http
      .get<Map<string, number>>(url)
      .pipe(
        map((data) => {
          return new Map<string, number>(Object.entries(data));
        })
      )
      .pipe(
        catchError(
          this.handleError<Map<string, number>>(
            'getProjectUnfinishedTasks',
            undefined
          )
        )
      );
  }

  public getEmptyProject(): Project {
    return new Object({
      id: '',
      name: '',
      description: '',
      startDate: new Date(),
      endDate: new Date(),
    }) as Project;
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  private projectFromProject(data: Project): Project {
    class MyProject implements Project {
      constructor(
        public id: string,
        public name: string,
        public description: string,
        public startDate: Date,
        public endDate: Date
      ) { }
    }

    if (data !== undefined)
      return new MyProject(
        data.id,
        data.name,
        data.description,
        new Date(data.startDate),
        new Date(data.endDate)
      );
    else
      return data;
  }

}


