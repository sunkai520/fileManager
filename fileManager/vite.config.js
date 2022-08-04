import {
  defineConfig,
  loadEnv
} from 'vite'
import vue from '@vitejs/plugin-vue'
import { join } from 'path';
function resolve(dir){
  return join(__dirname,dir)
}
// https://vitejs.dev/config/
export default (({
  mode
}) => {
  const ed = loadEnv(mode, process.cwd());
  console.log(ed.VITE_API_URL, "当前环境变量")
  return defineConfig({
    plugins: [vue()],
    server: {
      proxy: {
        '/api': {
          target: ed.VITE_API_URL,
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, '')
        }
      }
    },
    define: {

    },
    resolve:{
      alias:{
        '@':resolve('src')
      }
    }
  })
})