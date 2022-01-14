import { IOrganization } from '@/shared/model/organization.model';
import { IProjectPosition } from '@/shared/model/project-position.model';

export interface IProject {
  id?: number;
  title?: string;
  description?: string | null;
  start?: Date | null;
  end?: Date | null;
  active?: boolean;
  notes?: string | null;
  organization?: IOrganization;
  projectPositions?: IProjectPosition[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public start?: Date | null,
    public end?: Date | null,
    public active?: boolean,
    public notes?: string | null,
    public organization?: IOrganization,
    public projectPositions?: IProjectPosition[]
  ) {
    this.active = this.active ?? false;
  }
}
