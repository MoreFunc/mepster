<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="mepsterApp.organization.home.createOrEditLabel"
          data-cy="OrganizationCreateUpdateHeading"
          v-text="$t('mepsterApp.organization.home.createOrEditLabel')"
        >
          Create or edit a Organization
        </h2>
        <div>
          <div class="form-group" v-if="organization.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="organization.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.name')" for="organization-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="organization-name"
              data-cy="name"
              :class="{ valid: !$v.organization.name.$invalid, invalid: $v.organization.name.$invalid }"
              v-model="$v.organization.name.$model"
              required
            />
            <div v-if="$v.organization.name.$anyDirty && $v.organization.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.organization.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.name.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.name.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.street')" for="organization-street">Street</label>
            <input
              type="text"
              class="form-control"
              name="street"
              id="organization-street"
              data-cy="street"
              :class="{ valid: !$v.organization.street.$invalid, invalid: $v.organization.street.$invalid }"
              v-model="$v.organization.street.$model"
            />
            <div v-if="$v.organization.street.$anyDirty && $v.organization.street.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.street.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.number')" for="organization-number">Number</label>
            <input
              type="text"
              class="form-control"
              name="number"
              id="organization-number"
              data-cy="number"
              :class="{ valid: !$v.organization.number.$invalid, invalid: $v.organization.number.$invalid }"
              v-model="$v.organization.number.$model"
            />
            <div v-if="$v.organization.number.$anyDirty && $v.organization.number.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.number.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 10 })"
              >
                This field cannot be longer than 10 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.city')" for="organization-city">City</label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="organization-city"
              data-cy="city"
              :class="{ valid: !$v.organization.city.$invalid, invalid: $v.organization.city.$invalid }"
              v-model="$v.organization.city.$model"
            />
            <div v-if="$v.organization.city.$anyDirty && $v.organization.city.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.city.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 20 })"
              >
                This field cannot be longer than 20 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.city.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'City' })"
              >
                This field should follow pattern for "City".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.zipcode')" for="organization-zipcode">Zipcode</label>
            <input
              type="text"
              class="form-control"
              name="zipcode"
              id="organization-zipcode"
              data-cy="zipcode"
              :class="{ valid: !$v.organization.zipcode.$invalid, invalid: $v.organization.zipcode.$invalid }"
              v-model="$v.organization.zipcode.$model"
            />
            <div v-if="$v.organization.zipcode.$anyDirty && $v.organization.zipcode.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.zipcode.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 8 })"
              >
                This field cannot be longer than 8 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.zipcode.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Zipcode' })"
              >
                This field should follow pattern for "Zipcode".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.country')" for="organization-country">Country</label>
            <input
              type="text"
              class="form-control"
              name="country"
              id="organization-country"
              data-cy="country"
              :class="{ valid: !$v.organization.country.$invalid, invalid: $v.organization.country.$invalid }"
              v-model="$v.organization.country.$model"
            />
            <div v-if="$v.organization.country.$anyDirty && $v.organization.country.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.country.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 20 })"
              >
                This field cannot be longer than 20 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.country.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Country' })"
              >
                This field should follow pattern for "Country".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.phoneNumber')" for="organization-phoneNumber"
              >Phone Number</label
            >
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="organization-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.organization.phoneNumber.$invalid, invalid: $v.organization.phoneNumber.$invalid }"
              v-model="$v.organization.phoneNumber.$model"
            />
            <div v-if="$v.organization.phoneNumber.$anyDirty && $v.organization.phoneNumber.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.phoneNumber.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.phoneNumber.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Phone Number' })"
              >
                This field should follow pattern for "Phone Number".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.email')" for="organization-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="organization-email"
              data-cy="email"
              :class="{ valid: !$v.organization.email.$invalid, invalid: $v.organization.email.$invalid }"
              v-model="$v.organization.email.$model"
            />
            <div v-if="$v.organization.email.$anyDirty && $v.organization.email.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.email.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.email.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Email' })"
              >
                This field should follow pattern for "Email".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.website')" for="organization-website">Website</label>
            <input
              type="text"
              class="form-control"
              name="website"
              id="organization-website"
              data-cy="website"
              :class="{ valid: !$v.organization.website.$invalid, invalid: $v.organization.website.$invalid }"
              v-model="$v.organization.website.$model"
            />
            <div v-if="$v.organization.website.$anyDirty && $v.organization.website.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organization.website.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organization.website.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Website' })"
              >
                This field should follow pattern for "Website".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('mepsterApp.organization.notes')" for="organization-notes">Notes</label>
            <textarea
              class="form-control"
              name="notes"
              id="organization-notes"
              data-cy="notes"
              :class="{ valid: !$v.organization.notes.$invalid, invalid: $v.organization.notes.$invalid }"
              v-model="$v.organization.notes.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('mepsterApp.organization.parentOrganization')"
              for="organization-parentOrganization"
              >Parent Organization</label
            >
            <select
              class="form-control"
              id="organization-parentOrganization"
              data-cy="parentOrganization"
              name="parentOrganization"
              v-model="organization.parentOrganization"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  organization.parentOrganization && organizationOption.id === organization.parentOrganization.id
                    ? organization.parentOrganization
                    : organizationOption
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
            :disabled="$v.organization.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./organization-update.component.ts"></script>
