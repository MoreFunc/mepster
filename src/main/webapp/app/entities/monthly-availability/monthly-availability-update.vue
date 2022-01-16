<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.monthlyAvailability.home.createOrEditLabel"
          data-cy="MonthlyAvailabilityCreateUpdateHeading"
          v-text="$t('mepsterApp.monthlyAvailability.home.createOrEditLabel')"
        >
          Create or edit a MonthlyAvailability
        </h2>
        <div>
          <div class="form-group" v-if="monthlyAvailability.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="monthlyAvailability.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.monthlyAvailability.yearmonth')" for="monthly-availability-yearmonth"
              >Yearmonth</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="monthly-availability-yearmonth"
                  v-model="$v.monthlyAvailability.yearmonth.$model"
                  name="yearmonth"
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
                id="monthly-availability-yearmonth"
                data-cy="yearmonth"
                type="text"
                class="form-control"
                name="yearmonth"
                :class="{ valid: !$v.monthlyAvailability.yearmonth.$invalid, invalid: $v.monthlyAvailability.yearmonth.$invalid }"
                v-model="$v.monthlyAvailability.yearmonth.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.monthlyAvailability.percent')" for="monthly-availability-percent"
              >Percent</label
            >
            <input
              type="number"
              class="form-control"
              name="percent"
              id="monthly-availability-percent"
              data-cy="percent"
              :class="{ valid: !$v.monthlyAvailability.percent.$invalid, invalid: $v.monthlyAvailability.percent.$invalid }"
              v-model.number="$v.monthlyAvailability.percent.$model"
            />
            <div v-if="$v.monthlyAvailability.percent.$anyDirty && $v.monthlyAvailability.percent.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyAvailability.percent.min"
                v-text="$t('entity.validation.min', { min: 0 })"
              >
                This field should be at least 0.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyAvailability.percent.max"
                v-text="$t('entity.validation.max', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.monthlyAvailability.percent.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.monthlyAvailability.isActive')" for="monthly-availability-isActive"
              >Is Active</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="monthly-availability-isActive"
              data-cy="isActive"
              :class="{ valid: !$v.monthlyAvailability.isActive.$invalid, invalid: $v.monthlyAvailability.isActive.$invalid }"
              v-model="$v.monthlyAvailability.isActive.$model"
              required
            />
            <div v-if="$v.monthlyAvailability.isActive.$anyDirty && $v.monthlyAvailability.isActive.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyAvailability.isActive.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.monthlyAvailability.person')" for="monthly-availability-person"
              >Person</label
            >
            <select
              class="form-control"
              id="monthly-availability-person"
              data-cy="person"
              name="person"
              v-model="monthlyAvailability.person"
              required
            >
              <option v-if="!monthlyAvailability.person" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  monthlyAvailability.person && personOption.id === monthlyAvailability.person.id
                    ? monthlyAvailability.person
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.lastname }}
              </option>
            </select>
          </div>
          <div v-if="$v.monthlyAvailability.person.$anyDirty && $v.monthlyAvailability.person.$invalid">
            <small class="form-text text-danger" v-if="!$v.monthlyAvailability.person.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.monthlyAvailability.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./monthly-availability-update.component.ts"></script>
