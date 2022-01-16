<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.role.home.createOrEditLabel"
          data-cy="RoleCreateUpdateHeading"
          v-text="$t('mepsterApp.role.home.createOrEditLabel')"
        >
          Create or edit a Role
        </h2>
        <div>
          <div class="form-group" v-if="role.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="role.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.role.title')" for="role-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="role-title"
              data-cy="title"
              :class="{ valid: !$v.role.title.$invalid, invalid: $v.role.title.$invalid }"
              v-model="$v.role.title.$model"
              required
            />
            <div v-if="$v.role.title.$anyDirty && $v.role.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.role.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.role.title.minLength" v-text="$t('entity.validation.minlength', { min: 2 })">
                This field is required to be at least 2 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.role.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 40 })">
                This field cannot be longer than 40 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.role.person')" for="role-person">Person</label>
            <select class="form-control" id="role-person" data-cy="person" name="person" v-model="role.person">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="role.person && personOption.id === role.person.id ? role.person : personOption"
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.id }}
              </option>
            </select>
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
            :disabled="$v.role.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./role-update.component.ts"></script>
