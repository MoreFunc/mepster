/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';
import { MonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('MonthlyProjectPositionAssignment Service', () => {
    let service: MonthlyProjectPositionAssignmentService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new MonthlyProjectPositionAssignmentService();
      currentDate = new Date();
      elemDefault = new MonthlyProjectPositionAssignment(123, currentDate, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            yearmonth: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a MonthlyProjectPositionAssignment', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            yearmonth: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            yearmonth: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a MonthlyProjectPositionAssignment', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a MonthlyProjectPositionAssignment', async () => {
        const returnedFromService = Object.assign(
          {
            yearmonth: dayjs(currentDate).format(DATE_FORMAT),
            percent: 1,
            isActive: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            yearmonth: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a MonthlyProjectPositionAssignment', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a MonthlyProjectPositionAssignment', async () => {
        const patchObject = Object.assign(
          {
            yearmonth: dayjs(currentDate).format(DATE_FORMAT),
            percent: 1,
          },
          new MonthlyProjectPositionAssignment()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            yearmonth: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a MonthlyProjectPositionAssignment', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of MonthlyProjectPositionAssignment', async () => {
        const returnedFromService = Object.assign(
          {
            yearmonth: dayjs(currentDate).format(DATE_FORMAT),
            percent: 1,
            isActive: true,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            yearmonth: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of MonthlyProjectPositionAssignment', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a MonthlyProjectPositionAssignment', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a MonthlyProjectPositionAssignment', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
