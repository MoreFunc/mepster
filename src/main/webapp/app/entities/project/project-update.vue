<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.project.home.createOrEditLabel"
          data-cy="ProjectCreateUpdateHeading"
          v-text="$t('mepsterApp.project.home.createOrEditLabel')"
        >
          Create or edit a Project
        </h2>
        <div>
          <div class="form-group" v-if="project.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="project.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.title')" for="project-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="project-title"
              data-cy="title"
              :class="{ valid: !$v.project.title.$invalid, invalid: $v.project.title.$invalid }"
              v-model="$v.project.title.$model"
              required
            />
            <div v-if="$v.project.title.$anyDirty && $v.project.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.project.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.project.title.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.project.title.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.description')" for="project-description">Description</label>
            <textarea
              class="form-control"
              name="description"
              id="project-description"
              data-cy="description"
              :class="{ valid: !$v.project.description.$invalid, invalid: $v.project.description.$invalid }"
              v-model="$v.project.description.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.start')" for="project-start">Start</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-start"
                  v-model="$v.project.start.$model"
                  name="start"
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
                id="project-start"
                data-cy="start"
                type="text"
                class="form-control"
                name="start"
                :class="{ valid: !$v.project.start.$invalid, invalid: $v.project.start.$invalid }"
                v-model="$v.project.start.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.end')" for="project-end">End</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-end"
                  v-model="$v.project.end.$model"
                  name="end"
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
                id="project-end"
                data-cy="end"
                type="text"
                class="form-control"
                name="end"
                :class="{ valid: !$v.project.end.$invalid, invalid: $v.project.end.$invalid }"
                v-model="$v.project.end.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.active')" for="project-active">Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="active"
              id="project-active"
              data-cy="active"
              :class="{ valid: !$v.project.active.$invalid, invalid: $v.project.active.$invalid }"
              v-model="$v.project.active.$model"
              required
            />
            <div v-if="$v.project.active.$anyDirty && $v.project.active.$invalid">
              <small class="form-text text-danger" v-if="!$v.project.active.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.notes')" for="project-notes">Notes</label>
            <textarea
              class="form-control"
              name="notes"
              id="project-notes"
              data-cy="notes"
              :class="{ valid: !$v.project.notes.$invalid, invalid: $v.project.notes.$invalid }"
              v-model="$v.project.notes.$model"
            ></textarea>
          </div>
          <div v-if="$v.project.projectPositions.$anyDirty && $v.project.projectPositions.$invalid">
            <small class="form-text text-danger" v-if="!$v.project.projectPositions.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.project.organization')" for="project-organization">Organization</label>
            <select
              class="form-control"
              id="project-organization"
              data-cy="organization"
              name="organization"
              v-model="project.organization"
              required
            >
              <option v-if="!project.organization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  project.organization && organizationOption.id === project.organization.id ? project.organization : organizationOption
                "
                v-for="organizationOption in organizations"
                :key="organizationOption.id"
              >
                {{ organizationOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.project.organization.$anyDirty && $v.project.organization.$invalid">
            <small class="form-text text-danger" v-if="!$v.project.organization.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.project.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./project-update.component.ts"></script>
