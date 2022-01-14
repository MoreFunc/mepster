import { IProjectPosition } from '@/shared/model/project-position.model';

export interface IRole {
  id?: number;
  title?: string;
  projectPosition?: IProjectPosition | null;
}

export class Role implements IRole {
  constructor(public id?: number, public title?: string, public projectPosition?: IProjectPosition | null) {}
}
