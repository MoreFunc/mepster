<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.person.home.createOrEditLabel"
          data-cy="PersonCreateUpdateHeading"
          v-text="$t('mepsterApp.person.home.createOrEditLabel')"
        >
          Create or edit a Person
        </h2>
        <div>
          <div class="form-group" v-if="person.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="person.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.firstname')" for="person-firstname">Firstname</label>
            <input
              type="text"
              class="form-control"
              name="firstname"
              id="person-firstname"
              data-cy="firstname"
              :class="{ valid: !$v.person.firstname.$invalid, invalid: $v.person.firstname.$invalid }"
              v-model="$v.person.firstname.$model"
              required
            />
            <div v-if="$v.person.firstname.$anyDirty && $v.person.firstname.$invalid">
              <small class="form-text text-danger" v-if="!$v.person.firstname.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.person.firstname.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.person.firstname.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.lastname')" for="person-lastname">Lastname</label>
            <input
              type="text"
              class="form-control"
              name="lastname"
              id="person-lastname"
              data-cy="lastname"
              :class="{ valid: !$v.person.lastname.$invalid, invalid: $v.person.lastname.$invalid }"
              v-model="$v.person.lastname.$model"
              required
            />
            <div v-if="$v.person.lastname.$anyDirty && $v.person.lastname.$invalid">
              <small class="form-text text-danger" v-if="!$v.person.lastname.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.person.lastname.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.person.lastname.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.type')" for="person-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="person-type"
              data-cy="type"
              :class="{ valid: !$v.person.type.$invalid, invalid: $v.person.type.$invalid }"
              v-model="$v.person.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.lead')" for="person-lead">Lead</label>
            <input
              type="text"
              class="form-control"
              name="lead"
              id="person-lead"
              data-cy="lead"
              :class="{ valid: !$v.person.lead.$invalid, invalid: $v.person.lead.$invalid }"
              v-model="$v.person.lead.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.phoneNumber')" for="person-phoneNumber">Phone Number</label>
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="person-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.person.phoneNumber.$invalid, invalid: $v.person.phoneNumber.$invalid }"
              v-model="$v.person.phoneNumber.$model"
            />
            <div v-if="$v.person.phoneNumber.$anyDirty && $v.person.phoneNumber.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.person.phoneNumber.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.person.phoneNumber.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Phone Number' })"
              >
                This field should follow pattern for "Phone Number".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.email')" for="person-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="person-email"
              data-cy="email"
              :class="{ valid: !$v.person.email.$invalid, invalid: $v.person.email.$invalid }"
              v-model="$v.person.email.$model"
            />
            <div v-if="$v.person.email.$anyDirty && $v.person.email.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.person.email.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.person.email.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Email' })"
              >
                This field should follow pattern for "Email".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.notes')" for="person-notes">Notes</label>
            <textarea
              class="form-control"
              name="notes"
              id="person-notes"
              data-cy="notes"
              :class="{ valid: !$v.person.notes.$invalid, invalid: $v.person.notes.$invalid }"
              v-model="$v.person.notes.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.person.organization')" for="person-organization">Organization</label>
            <select class="form-control" id="person-organization" data-cy="organization" name="organization" v-model="person.organization">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  person.organization && organizationOption.id === person.organization.id ? person.organization : organizationOption
                "
                v-for="organizationOption in organizations"
                :key="organizationOption.id"
              >
                {{ organizationOption.name }}
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
            :disabled="$v.person.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./person-update.component.ts"></script>
