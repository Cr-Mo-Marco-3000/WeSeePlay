<template>
  <div class="delete-text">
    <span>회원 삭제</span>
    <p>
      삭제 후에는 계정을 다시 이용할 수 없습니다 <br />정말로 삭제를 원하신다면
      아래 문구를 입력해 주세요
    </p>
  </div>
  <div class="user-input">
    <span>계정 삭제에 동의합니다.</span>
    <input
      type="text"
      v-model="confirmationText"
      placeholder="위의 문구를 입력해 주세요"
    />
  </div>
  <button
    @click.prevent="deleteUser"
    class="overlay__btn pw-change-btn"
    style="margin-top: 0"
  >
    계정삭제
  </button>
</template>

<script>
import Swal from "sweetalert2"
import { ref } from "vue"
import axios from "axios"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import api from "@/api/api"

export default {
  name: "DeleteUserModalContent",
  setup() {
    const confirmationText = ref("")

    const store = useStore()
    const router = useRouter()

    const deleteUser = async function () {
      try {
        if (confirmationText.value !== "계정 삭제에 동의합니다.") {
          Swal.fire({
            icon: "error",
            text: "확인문구가 틀렸습니다",
          })
          return
        }

        const response = await axios({
          url: api.users.deleteUser(),
          method: "DELETE",
          headers: store.getters.authHeader,
        })

        console.log(response)
        if (response.status === 200) {
          Swal.fire({
            icon: "success",
            text: "계정 삭제가 완료되었습니다. 다시 만나요!",
          })
          store.dispatch("logout")
        }
      } catch (error) {
        if (error.response.status === 401) {
          Swal.fire({
            icon: "error",
            text: "오류가 발생했습니다. 다시 시도해 주세요.",
          })
          store.dispatch("logout")
        } else if (error.response.status === 404) {
          Swal.fire({
            icon: "error",
            text: "존재하지 않는 계정입니다. 다시 시도해 주세요.",
          })
          store.dispatch("logout")
        } else {
          router.push({ name: "errorpage", params: { errorname: 500 } })
        }
      }
    }

    return { confirmationText, deleteUser }
  },
}
</script>

<style></style>
