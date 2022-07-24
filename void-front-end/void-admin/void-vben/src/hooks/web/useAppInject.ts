import { useAppProviderContext } from '/@/components/Application';
import { computed, unref } from '../../../node_modules/vue';

export function useAppInject() {
  const values = useAppProviderContext();

  return {
    getIsMobile: computed(() => unref(values.isMobile)),
  };
}
