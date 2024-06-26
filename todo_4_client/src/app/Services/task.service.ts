import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';
import { Task } from '../Types/Task';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private projectUrl = `${environment.apiUrl}/projects`;
  private taskUrl = 'tasks';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };
  constructor(private http: HttpClient) {}

  public getTasks(projectID: string): Observable<Task[]> {
    const url = `${this.projectUrl}/${projectID}/${this.taskUrl}/all`;
    return this.http
      .get<Task[]>(url)
      .pipe(catchError(this.handleError<Task[]>('getTasks', [])));
  }

  public getTask(projectID: string, id: string): Observable<Task> {
    const url = `${this.projectUrl}/${projectID}/${this.taskUrl}/${id}`;
    return this.http
      .get<Task>(url)
      .pipe(catchError(this.handleError<Task>('getProject', undefined)));
  }

  public updateTask(
    task: Task,
    projectID: string,
    id: string
  ): Observable<any> {
    const url = `${this.projectUrl}/${projectID}/${this.taskUrl}/${id}`;
    return this.http
      .put(url, task, this.httpOptions)
      .pipe(catchError(this.handleError<any>('updateTask')));
  }

  public addTask(task: Task, projectID: string): Observable<Task> {
    const url = `${this.projectUrl}/${projectID}/${this.taskUrl}`;
    return this.http
      .post<Task>(url, task, this.httpOptions)
      .pipe(catchError(this.handleError<Task>('addTask', undefined)));
  }

  public deleteTask(projectID: string, id: string): Observable<void> {
    const url = `${this.projectUrl}/${projectID}/${this.taskUrl}/${id}`;
    return this.http
      .delete<void>(url, this.httpOptions)
      .pipe(catchError(this.handleError<void>('deleteTask', undefined)));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
