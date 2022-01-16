<template>
  <div>
    <h2 id="page-heading" data-cy="ProjectPositionHeading">
      <span v-text="$t('mepsterApp.projectPosition.home.title')" id="project-position-heading">Project Positions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mepsterApp.projectPosition.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProjectPositionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-project-position"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mepsterApp.projectPosition.home.createLabel')"> Create a new Project Position </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && projectPositions && projectPositions.length === 0">
      <span v-text="$t('mepsterApp.projectPosition.home.notFound')">No projectPositions found</span>
    </div>
    <div class="table-responsive" v-if="projectPositions && projectPositions.length > 0">
      <table class="table table-striped" aria-describedby="projectPositions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.title')">Title</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.description')">Description</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.startDate')">Start Date</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.endDate')">End Date</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.percent')">Percent</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.role')">Role</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.projectPosition.project')">Project</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="projectPosition in projectPositions" :key="projectPosition.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProjectPositionView', params: { projectPositionId: projectPosition.id } }">{{
                projectPosition.id
              }}</router-link>
            </td>
            <td>{{ projectPosition.title }}</td>
            <td>{{ projectPosition.description }}</td>
            <td>{{ projectPosition.startDate }}</td>
            <td>{{ projectPosition.endDate }}</td>
            <td>{{ projectPosition.percent }}</td>
            <td>
              <div v-if="projectPosition.role">
                <router-link :to="{ name: 'RoleView', params: { roleId: projectPosition.role.id } }">{{
                  projectPosition.role.title
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="projectPosition.project">
                <router-link :to="{ name: 'ProjectView', params: { projectId: projectPosition.project.id } }">{{
                  projectPosition.project.title
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ProjectPositionView', params: { projectPositionId: projectPosition.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ProjectPositionEdit', params: { projectPositionId: projectPosition.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(projectPosition)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="mepsterApp.projectPosition.delete.question"
          data-cy="projectPositionDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-projectPosition-heading" v-text="$t('mepsterApp.projectPosition.delete.question', { id: removeId })">
          Are you sure you want to delete this Project Position?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-projectPosition"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProjectPosition()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./project-position.component.ts"></script>
