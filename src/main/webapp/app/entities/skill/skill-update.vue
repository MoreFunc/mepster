<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.skill.home.createOrEditLabel"
          data-cy="SkillCreateUpdateHeading"
          v-text="$t('mepsterApp.skill.home.createOrEditLabel')"
        >
          Create or edit a Skill
        </h2>
        <div>
          <div class="form-group" v-if="skill.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="skill.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.skill.title')" for="skill-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="skill-title"
              data-cy="title"
              :class="{ valid: !$v.skill.title.$invalid, invalid: $v.skill.title.$invalid }"
              v-model="$v.skill.title.$model"
              required
            />
            <div v-if="$v.skill.title.$anyDirty && $v.skill.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.title.minLength" v-text="$t('entity.validation.minlength', { min: 2 })">
                This field is required to be at least 2 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 30 })">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.skill.description')" for="skill-description">Description</label>
            <textarea
              class="form-control"
              name="description"
              id="skill-description"
              data-cy="description"
              :class="{ valid: !$v.skill.description.$invalid, invalid: $v.skill.description.$invalid }"
              v-model="$v.skill.description.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.skill.projectPosition')" for="skill-projectPosition"
              >Project Position</label
            >
            <select
              class="form-control"
              id="skill-projectPosition"
              data-cy="projectPosition"
              name="projectPosition"
              v-model="skill.projectPosition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  skill.projectPosition && projectPositionOption.id === skill.projectPosition.id
                    ? skill.projectPosition
                    : projectPositionOption
                "
                v-for="projectPositionOption in projectPositions"
                :key="projectPositionOption.id"
              >
                {{ projectPositionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.skill.person')" for="skill-person">Person</label>
            <select class="form-control" id="skill-person" data-cy="person" name="person" v-model="skill.person">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="skill.person && personOption.id === skill.person.id ? skill.person : personOption"
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
            :disabled="$v.skill.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./skill-update.component.ts"></script>
