import { IProjectPosition } from '@/shared/model/project-position.model';
import { IPerson } from '@/shared/model/person.model';

export interface IRole {
  id?: number;
  title?: string;
  projectPosition?: IProjectPosition | null;
  person?: IPerson | null;
}

export class Role implements IRole {
  constructor(
    public id?: number,
    public title?: string,
    public projectPosition?: IProjectPosition | null,
    public person?: IPerson | null
  ) {}
}
