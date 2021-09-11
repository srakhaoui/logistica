import { Input, Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'jhi-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit, OnDestroy {

  @Input() endpoint: string;

  fileName = '';
  uploadProgress:number;
  uploadSub: Subscription;

  constructor(private http: HttpClient) {}

  onFileSelected(event) {
      const file:File = event.target.files[0];

      if (file) {
          this.fileName = file.name;
          const formData = new FormData();
          formData.append("file", file);

          const upload$ = this.http.post(this.endpoint, formData, {
              reportProgress: true,
              observe: 'events'
          })
          .pipe(
              finalize(() => this.reset())
          );

          this.uploadSub = upload$.subscribe(uploadEvent => {
            if (uploadEvent.type === HttpEventType.UploadProgress) {
              this.uploadProgress = Math.round(100 * (uploadEvent.loaded / uploadEvent.total));
            }
          })
      }
  }

  cancelUpload() {
    this.uploadSub.unsubscribe();
    this.reset();
  }

  reset() {
    this.uploadProgress = null;
    this.uploadSub = null;
  }

  ngOnInit() {
  }
  ngOnDestroy() {
    this.uploadSub.unsubscribe();
  }
}
