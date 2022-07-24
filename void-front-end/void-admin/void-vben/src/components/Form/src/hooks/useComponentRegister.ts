import type { ComponentType } from '../types';
import { tryOnUnmounted } from '@vueuse/core';
import { add, del } from '../componentMap';
import type { Component } from '../../../../../node_modules/vue';

export function useComponentRegister(compName: ComponentType, comp: Component) {
  add(compName, comp);
  tryOnUnmounted(() => {
    del(compName);
  });
}
