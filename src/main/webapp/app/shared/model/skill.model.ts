import { IProjectPosition } from '@/shared/model/project-position.model';
import { IPerson } from '@/shared/model/person.model';

export interface ISkill {
  id?: number;
  title?: string;
  description?: string | null;
  projectPositions?: IProjectPosition[] | null;
  persons?: IPerson[] | null;
}

export class Skill implements ISkill {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public projectPositions?: IProjectPosition[] | null,
    public persons?: IPerson[] | null
  ) {}
}
