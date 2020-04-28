import { Moment } from 'moment';

export interface IAudit {
  createdBy?: String;
  createdOn?: Moment;
  lastModifiedBy?: string;
  lastModifiedOn?: Moment;
}
