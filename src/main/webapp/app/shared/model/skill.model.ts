import { IProjectPosition } from '@/shared/model/project-position.model';

export interface ISkill {
  id?: number;
  title?: string;
  description?: string | null;
  projectPosition?: IProjectPosition | null;
}

export class Skill implements ISkill {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public projectPosition?: IProjectPosition | null
  ) {}
}
