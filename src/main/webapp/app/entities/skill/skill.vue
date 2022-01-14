<template>
  <div>
    <h2 id="page-heading" data-cy="SkillHeading">
      <span v-text="$t('mepsterApp.skill.home.title')" id="skill-heading">Skills</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mepsterApp.skill.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SkillCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-skill"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mepsterApp.skill.home.createLabel')"> Create a new Skill </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && skills && skills.length === 0">
      <span v-text="$t('mepsterApp.skill.home.notFound')">No skills found</span>
    </div>
    <div class="table-responsive" v-if="skills && skills.length > 0">
      <table class="table table-striped" aria-describedby="skills">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.skill.title')">Title</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.skill.description')">Description</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.skill.projectPosition')">Project Position</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="skill in skills" :key="skill.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SkillView', params: { skillId: skill.id } }">{{ skill.id }}</router-link>
            </td>
            <td>{{ skill.title }}</td>
            <td>{{ skill.description }}</td>
            <td>
              <div v-if="skill.projectPosition">
                <router-link :to="{ name: 'ProjectPositionView', params: { projectPositionId: skill.projectPosition.id } }">{{
                  skill.projectPosition.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SkillView', params: { skillId: skill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SkillEdit', params: { skillId: skill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(skill)"
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
        ><span id="mepsterApp.skill.delete.question" data-cy="skillDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-skill-heading" v-text="$t('mepsterApp.skill.delete.question', { id: removeId })">
          Are you sure you want to delete this Skill?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-skill"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSkill()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./skill.component.ts"></script>
