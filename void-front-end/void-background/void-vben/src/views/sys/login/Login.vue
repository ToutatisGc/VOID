<template>
  <div :class="prefixCls" class="w-full h-full px-4">
    <AppDarkModeToggle class="absolute top-3 right-5 enter-x" v-if="!sessionTimeout" />
    <AppLogo :alwaysShowTitle="true"/>
    <span class="-enter-x xl:hidden">
      <AppLogo :alwaysShowTitle="true" />
    </span>
    <div :class="`${prefixCls}_dark`" class="relative lg:top-1/3 top-15 lg:w-500px lg:h-auto py-5 mx-auto px-6
     bg-gray-100 rounded-2xl shadow-md">
      <div style="background-color:red;height:50px;width:50px;position:absolute;right:15px"></div>
      <LoginForm />
      <RegisterForm />
      <ForgetPasswordForm />
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed } from 'vue';
import { AppLogo } from '/@/components/Application';
import { AppLocalePicker, AppDarkModeToggle } from '/@/components/Application';
import LoginForm from './LoginForm.vue';
import ForgetPasswordForm from './ForgetPasswordForm.vue';
import RegisterForm from './RegisterForm.vue';
import MobileForm from './MobileForm.vue';
import QrCodeForm from './QrCodeForm.vue';
import { useGlobSetting } from '/@/hooks/setting';
import { useI18n } from '/@/hooks/web/useI18n';
import { useDesign } from '/@/hooks/web/useDesign';
import { useLocaleStore } from '/@/store/modules/locale';

// import { LoginStateEnum, useLoginState, useFormRules, useFormValid } from './useLogin';
// const { setLoginState, getLoginState } = useLoginState();


defineProps({
  sessionTimeout: {
    type: Boolean,
  },
});

const globSetting = useGlobSetting();
const { prefixCls } = useDesign('login');
const { t } = useI18n();
const localeStore = useLocaleStore();
const showLocale = localeStore.getShowPicker;
const title = computed(() => globSetting?.title ?? '');
</script>
<style lang="less">
@prefix-cls: ~'@{namespace}-login';
@logo-prefix-cls: ~'@{namespace}-app-logo';
@countdown-prefix-cls: ~'@{namespace}-countdown-input';
@dark-bg: #293146;

html[data-theme='dark'] {
  .@{prefix-cls} {
    background-color: @dark-bg;

    .ant-input,
    .ant-input-password {
      background-color: #232a3b;
    }

    .ant-btn:not(.ant-btn-link):not(.ant-btn-primary) {
      border: 1px solid #4a5569;
    }

    &-form {
      background: transparent !important;
    }

    .app-iconify {
      color: #fff;
    }

    &_dark{
      background-color: black;
    }
  }

  input.fix-auto-fill,
  .fix-auto-fill input {
    -webkit-text-fill-color: #c9d1d9 !important;
    box-shadow: inherit !important;
  }
}

.@{prefix-cls} {
  min-height: 100%;
  overflow: hidden;
  @media (max-width: @screen-xl) {
    background-color: #293146;

    .@{prefix-cls}-form {
      background-color: #fff;
    }
  }

  &::before {
    position: absolute;
    z-index: -1;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #000046;  /* fallback for old browsers */
    background: -webkit-linear-gradient(to left, #1CB5E0, #000046);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to left, #1CB5E0, #000046); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    content: '';
    @media (max-width: @screen-xl) {
      display: none;
    }
  }

  .@{logo-prefix-cls} {
    position: absolute;
    top: 12px;
    height: 30px;

    &__title {
      font-size: 16px;
      color: #fff;
    }

    img {
      width: 32px;
    }
  }

  .container {
    .@{logo-prefix-cls} {
      display: flex;
      width: 60%;
      height: 80px;

      &__title {
        font-size: 24px;
        color: #fff;
      }

      img {
        width: 48px;
      }
    }
  }

  &-sign-in-way {
    .anticon {
      font-size: 22px;
      color: #888;
      cursor: pointer;

      &:hover {
        color: @primary-color;
      }
    }
  }

  input:not([type='checkbox']) {
    min-width: 360px;

    @media (max-width: @screen-xl) {
      min-width: 320px;
    }

    @media (max-width: @screen-lg) {
      min-width: 260px;
    }

    @media (max-width: @screen-md) {
      min-width: 240px;
    }

    @media (max-width: @screen-sm) {
      min-width: 160px;
    }
  }

  .@{countdown-prefix-cls} input {
    min-width: unset;
  }

  .ant-divider-inner-text {
    font-size: 12px;
    color: @text-color-secondary;
  }
}
</style>
