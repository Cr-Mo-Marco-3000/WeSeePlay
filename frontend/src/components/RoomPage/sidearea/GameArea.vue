<template>
  <!-- 타이틀 -->
  <div class="title">게임</div>
  <!-- 본문 -->
  <div class="game-list-area">
    <div class="game-item" @click="$emit('click-liargame')">
      <div class="game-title game-text">라이어게임</div>
      <!-- 아래 부분의 game-text가 게임 하나 부분입니다. -->
      <div class="game-text">
        당신은 제시어에 대해 사람들에게 설명해야 합니다.<br />
        하지만 걱정하지 마세요. <br />
        모두에게 제시어가 공유되니까요.<br />
        한 사람, 라이어를 제외하면요!<br />
        누가 거짓말을 하고 있는 라이어일까요?<br />
      </div>
    </div>
    <div class="game-item" @click="$emit('click-callmyname')">
      <div class="game-title game-text">콜 마이 네임</div>
      <!-- 아래 부분의 game-text가 게임 하나 부분입니다. -->
      <div class="game-text">
        사람들은 다른 사람의 이름을 볼 수 있지만,<br />
        자신의 이름은 알 수 없습니다.<br />
        다른 사람들과의 질답을 통해<br />
        다른 사람들보다 빠르게 <br />
        자신이 누구인지 알아맞춰보세요!<br />
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style>
@import url("../../../../src/assets/roompage/sidearea.css");
</style>
