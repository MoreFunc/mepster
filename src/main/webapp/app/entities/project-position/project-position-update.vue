<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.projectPosition.home.createOrEditLabel"
          data-cy="ProjectPositionCreateUpdateHeading"
          v-text="$t('mepsterApp.projectPosition.home.createOrEditLabel')"
        >
          Create or edit a ProjectPosition
        </h2>
        <div>
          <div class="form-group" v-if="projectPosition.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="projectPosition.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.title')" for="project-position-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="project-position-title"
              data-cy="title"
              :class="{ valid: !$v.projectPosition.title.$invalid, invalid: $v.projectPosition.title.$invalid }"
              v-model="$v.projectPosition.title.$model"
              required
            />
            <div v-if="$v.projectPosition.title.$anyDirty && $v.projectPosition.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.projectPosition.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.projectPosition.title.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.projectPosition.title.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 40 })"
              >
                This field cannot be longer than 40 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.description')" for="project-position-description"
              >Description</label
            >
            <textarea
              class="form-control"
              name="description"
              id="project-position-description"
              data-cy="description"
              :class="{ valid: !$v.projectPosition.description.$invalid, invalid: $v.projectPosition.description.$invalid }"
              v-model="$v.projectPosition.description.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.startDate')" for="project-position-startDate"
              >Start Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-position-startDate"
                  v-model="$v.projectPosition.startDate.$model"
                  name="startDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="project-position-startDate"
                data-cy="startDate"
                type="text"
                class="form-control"
                name="startDate"
                :class="{ valid: !$v.projectPosition.startDate.$invalid, invalid: $v.projectPosition.startDate.$invalid }"
                v-model="$v.projectPosition.startDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.endDate')" for="project-position-endDate"
              >End Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-position-endDate"
                  v-model="$v.projectPosition.endDate.$model"
                  name="endDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="project-position-endDate"
                data-cy="endDate"
                type="text"
                class="form-control"
                name="endDate"
                :class="{ valid: !$v.projectPosition.endDate.$invalid, invalid: $v.projectPosition.endDate.$invalid }"
                v-model="$v.projectPosition.endDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.percent')" for="project-position-percent"
              >Percent</label
            >
            <input
              type="number"
              class="form-control"
              name="percent"
              id="project-position-percent"
              data-cy="percent"
              :class="{ valid: !$v.projectPosition.percent.$invalid, invalid: $v.projectPosition.percent.$invalid }"
              v-model.number="$v.projectPosition.percent.$model"
            />
            <div v-if="$v.projectPosition.percent.$anyDirty && $v.projectPosition.percent.$invalid">
              <small class="form-text text-danger" v-if="!$v.projectPosition.percent.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.projectPosition.percent.max"
                v-text="$t('entity.validation.max', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.projectPosition.percent.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.role')" for="project-position-role">Role</label>
            <select class="form-control" id="project-position-role" data-cy="role" name="role" v-model="projectPosition.role" required>
              <option v-if="!projectPosition.role" v-bind:value="null" selected></option>
              <option
                v-bind:value="projectPosition.role && roleOption.id === projectPosition.role.id ? projectPosition.role : roleOption"
                v-for="roleOption in roles"
                :key="roleOption.id"
              >
                {{ roleOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.projectPosition.role.$anyDirty && $v.projectPosition.role.$invalid">
            <small class="form-text text-danger" v-if="!$v.projectPosition.role.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label v-text="$t('mepsterApp.projectPosition.skills')" for="project-position-skills">Skills</label>
            <select
              class="form-control"
              id="project-position-skills"
              data-cy="skills"
              multiple
              name="skills"
              v-if="projectPosition.skills !== undefined"
              v-model="projectPosition.skills"
            >
              <option v-bind:value="getSelected(projectPosition.skills, skillOption)" v-for="skillOption in skills" :key="skillOption.id">
                {{ skillOption.title }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.projectPosition.project')" for="project-position-project"
              >Project</label
            >
            <select
              class="form-control"
              id="project-position-project"
              data-cy="project"
              name="project"
              v-model="projectPosition.project"
              required
            >
              <option v-if="!projectPosition.project" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  projectPosition.project && projectOption.id === projectPosition.project.id ? projectPosition.project : projectOption
                "
                v-for="projectOption in projects"
                :key="projectOption.id"
              >
                {{ projectOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.projectPosition.project.$anyDirty && $v.projectPosition.project.$invalid">
            <small class="form-text text-danger" v-if="!$v.projectPosition.project.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.projectPosition.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./project-position-update.component.ts"></script>
