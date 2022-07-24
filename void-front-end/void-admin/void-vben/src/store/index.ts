import type { App } from '../../node_modules/vue';
import { createPinia } from '../../node_modules/pinia';

const store = createPinia();

export function setupStore(app: App<Element>) {
  app.use(store);
}

export { store };
