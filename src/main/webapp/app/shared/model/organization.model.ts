import { IPerson } from '@/shared/model/person.model';
import { IProject } from '@/shared/model/project.model';

export interface IOrganization {
  id?: number;
  name?: string;
  street?: string | null;
  number?: string | null;
  city?: string | null;
  zipcode?: string | null;
  country?: string | null;
  phoneNumber?: string | null;
  email?: string | null;
  notes?: string | null;
  persons?: IPerson[] | null;
  projects?: IProject[] | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public street?: string | null,
    public number?: string | null,
    public city?: string | null,
    public zipcode?: string | null,
    public country?: string | null,
    public phoneNumber?: string | null,
    public email?: string | null,
    public notes?: string | null,
    public persons?: IPerson[] | null,
    public projects?: IProject[] | null
  ) {}
}
