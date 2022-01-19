import { IProjectPosition } from '@/shared/model/project-position.model';
import { IPerson } from '@/shared/model/person.model';

export interface IRole {
  id?: number;
  title?: string;
  projectPositions?: IProjectPosition[] | null;
  persons?: IPerson[] | null;
}

export class Role implements IRole {
  constructor(
    public id?: number,
    public title?: string,
    public projectPositions?: IProjectPosition[] | null,
    public persons?: IPerson[] | null
  ) {}
}
