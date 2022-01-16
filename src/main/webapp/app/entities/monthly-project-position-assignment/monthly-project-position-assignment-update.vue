<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.monthlyProjectPositionAssignment.home.createOrEditLabel"
          data-cy="MonthlyProjectPositionAssignmentCreateUpdateHeading"
          v-text="$t('mepsterApp.monthlyProjectPositionAssignment.home.createOrEditLabel')"
        >
          Create or edit a MonthlyProjectPositionAssignment
        </h2>
        <div>
          <div class="form-group" v-if="monthlyProjectPositionAssignment.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="monthlyProjectPositionAssignment.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('mepsterApp.monthlyProjectPositionAssignment.yearmonth')"
              for="monthly-project-position-assignment-yearmonth"
              >Yearmonth</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="monthly-project-position-assignment-yearmonth"
                  v-model="$v.monthlyProjectPositionAssignment.yearmonth.$model"
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
                id="monthly-project-position-assignment-yearmonth"
                data-cy="yearmonth"
                type="text"
                class="form-control"
                name="yearmonth"
                :class="{
                  valid: !$v.monthlyProjectPositionAssignment.yearmonth.$invalid,
                  invalid: $v.monthlyProjectPositionAssignment.yearmonth.$invalid,
                }"
                v-model="$v.monthlyProjectPositionAssignment.yearmonth.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('mepsterApp.monthlyProjectPositionAssignment.percent')"
              for="monthly-project-position-assignment-percent"
              >Percent</label
            >
            <input
              type="number"
              class="form-control"
              name="percent"
              id="monthly-project-position-assignment-percent"
              data-cy="percent"
              :class="{
                valid: !$v.monthlyProjectPositionAssignment.percent.$invalid,
                invalid: $v.monthlyProjectPositionAssignment.percent.$invalid,
              }"
              v-model.number="$v.monthlyProjectPositionAssignment.percent.$model"
            />
            <div v-if="$v.monthlyProjectPositionAssignment.percent.$anyDirty && $v.monthlyProjectPositionAssignment.percent.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyProjectPositionAssignment.percent.min"
                v-text="$t('entity.validation.min', { min: 0 })"
              >
                This field should be at least 0.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyProjectPositionAssignment.percent.max"
                v-text="$t('entity.validation.max', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyProjectPositionAssignment.percent.numeric"
                v-text="$t('entity.validation.number')"
              >
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('mepsterApp.monthlyProjectPositionAssignment.isActive')"
              for="monthly-project-position-assignment-isActive"
              >Is Active</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="monthly-project-position-assignment-isActive"
              data-cy="isActive"
              :class="{
                valid: !$v.monthlyProjectPositionAssignment.isActive.$invalid,
                invalid: $v.monthlyProjectPositionAssignment.isActive.$invalid,
              }"
              v-model="$v.monthlyProjectPositionAssignment.isActive.$model"
              required
            />
            <div v-if="$v.monthlyProjectPositionAssignment.isActive.$anyDirty && $v.monthlyProjectPositionAssignment.isActive.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.monthlyProjectPositionAssignment.isActive.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('mepsterApp.monthlyProjectPositionAssignment.projectPosition')"
              for="monthly-project-position-assignment-projectPosition"
              >Project Position</label
            >
            <select
              class="form-control"
              id="monthly-project-position-assignment-projectPosition"
              data-cy="projectPosition"
              name="projectPosition"
              v-model="monthlyProjectPositionAssignment.projectPosition"
              required
            >
              <option v-if="!monthlyProjectPositionAssignment.projectPosition" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  monthlyProjectPositionAssignment.projectPosition &&
                  projectPositionOption.id === monthlyProjectPositionAssignment.projectPosition.id
                    ? monthlyProjectPositionAssignment.projectPosition
                    : projectPositionOption
                "
                v-for="projectPositionOption in projectPositions"
                :key="projectPositionOption.id"
              >
                {{ projectPositionOption.title }}
              </option>
            </select>
          </div>
          <div
            v-if="
              $v.monthlyProjectPositionAssignment.projectPosition.$anyDirty && $v.monthlyProjectPositionAssignment.projectPosition.$invalid
            "
          >
            <small
              class="form-text text-danger"
              v-if="!$v.monthlyProjectPositionAssignment.projectPosition.required"
              v-text="$t('entity.validation.required')"
            >
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('mepsterApp.monthlyProjectPositionAssignment.person')"
              for="monthly-project-position-assignment-person"
              >Person</label
            >
            <select
              class="form-control"
              id="monthly-project-position-assignment-person"
              data-cy="person"
              name="person"
              v-model="monthlyProjectPositionAssignment.person"
              required
            >
              <option v-if="!monthlyProjectPositionAssignment.person" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  monthlyProjectPositionAssignment.person && personOption.id === monthlyProjectPositionAssignment.person.id
                    ? monthlyProjectPositionAssignment.person
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.lastname }}
              </option>
            </select>
          </div>
          <div v-if="$v.monthlyProjectPositionAssignment.person.$anyDirty && $v.monthlyProjectPositionAssignment.person.$invalid">
            <small
              class="form-text text-danger"
              v-if="!$v.monthlyProjectPositionAssignment.person.required"
              v-text="$t('entity.validation.required')"
            >
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
            :disabled="$v.monthlyProjectPositionAssignment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./monthly-project-position-assignment-update.component.ts"></script>
