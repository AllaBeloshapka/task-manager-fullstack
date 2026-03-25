const MAIN = document.querySelector(".main");
const TASKS = document.querySelector(".tasks");
const INPUT = document.querySelector(".input");
const INPUT_FILTER = document.querySelector(".input_filter");
const FILTER_BTN = document.querySelector("#filter-btn");
const CLEAR_BTN = document.querySelector("#clear-btn");
const NUMBER = document.querySelector(".number");

const BTN_MINUS = document.querySelector(".btn-minus");
const BTN_PLUS = document.querySelector(".btn-plus");
const BTN_UPDATE = document.querySelector(".btn-update");

let tasksArray = [];

// =======================
// 🔹 РЕНДЕР
// =======================
function renderTasks(tasks) {
  TASKS.innerHTML = "";

  tasks.forEach((task) => {
    const taskItem = document.createElement("div");
    taskItem.classList.add("task-item");

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";

    checkbox.addEventListener("change", () => {
      if (checkbox.checked) {
        INPUT.value = task.title;
        INPUT.dataset.id = task.id;
      } else {
        INPUT.value = "";
        delete INPUT.dataset.id;
      }
    });

    const taskText = document.createElement("span");
    taskText.textContent = task.title;

    taskItem.appendChild(checkbox);
    taskItem.appendChild(taskText);
    TASKS.appendChild(taskItem);
  });

  NUMBER.textContent = tasks.length;
}

// =======================
// 🔹 ЗАГРУЗКА С BACKEND
// =======================
async function loadTasks() {
  try {
    const response = await fetch("http://localhost:8080/tasks");
    const data = await response.json();

    tasksArray = data;
    renderTasks(tasksArray);
  } catch (error) {
    console.error("Ошибка загрузки задач:", error);
  }
}

// =======================
// 🔍 ПОИСК (через backend)
// =======================

// скрываем крестик при старте
CLEAR_BTN.style.display = "none";

// Очистка поля поиска и отображение всех задач
CLEAR_BTN.addEventListener("click", () => {
  INPUT_FILTER.value = "";
  renderTasks(tasksArray);
  CLEAR_BTN.style.display = "none";
  INPUT_FILTER.focus();
});

// Показать кнопку очистки при вводе текста
INPUT_FILTER.addEventListener("input", () => {
  if (INPUT_FILTER.value.trim() === "") {
    renderTasks(tasksArray);
    CLEAR_BTN.style.display = "none";
  } else {
    CLEAR_BTN.style.display = "inline-block";
  }
});

async function searchTasks() {
  const keyword = INPUT_FILTER.value.trim();

  if (!keyword) {
    renderTasks(tasksArray);
    return;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/tasks?keyword=${encodeURIComponent(keyword)}`
    );

    const data = await response.json();

    renderTasks(data);

  } catch (error) {
    console.error("Ошибка поиска:", error);
  }
}

// Вывод всех задач при очистке поля поиска
INPUT_FILTER.addEventListener("input", () => {
  if (INPUT_FILTER.value.trim() === "") {
    renderTasks(tasksArray);
  }
});

// =======================
// 🔘 КНОПКА ПОИСКА
// =======================
FILTER_BTN.addEventListener("click", () => {
  searchTasks();
});

// =======================
// ⌨️ ENTER ДЛЯ ПОИСКА
// =======================
INPUT_FILTER.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    searchTasks();
  }
});

// =======================
// ➕ ДОБАВИТЬ
// =======================
BTN_PLUS.addEventListener("click", async () => {
  const task = INPUT.value.trim();
  if (!task) return;

  try {
    const response = await fetch("http://localhost:8080/tasks", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ title: task })
    });

    const newTask = await response.json();

    tasksArray.push(newTask);

    INPUT.value = "";
    renderTasks(tasksArray);
  } catch (error) {
    console.error("Ошибка добавления:", error);
  }

  INPUT.focus();
});

// =======================
// ✏️ ОБНОВИТЬ
// =======================
BTN_UPDATE.addEventListener("click", async () => {
  const id = INPUT.dataset.id;
  const updatedTitle = INPUT.value.trim();

  if (!id || !updatedTitle) {
    INPUT.value = "";
    delete INPUT.dataset.id;
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ title: updatedTitle })
    });

    const updatedTask = await response.json();

    const index = tasksArray.findIndex(task => task.id == id);
    if (index !== -1) {
      tasksArray[index] = updatedTask;
    }

    INPUT.value = "";
    delete INPUT.dataset.id;

    renderTasks(tasksArray);
  } catch (error) {
    console.error("Ошибка обновления:", error);
  }

  INPUT.focus();
});

// =======================
// ❌ УДАЛИТЬ
// =======================
BTN_MINUS.addEventListener("click", async () => {
  const id = INPUT.dataset.id;

  if (!id) {
    INPUT.value = "";
    return;
  }

  try {
    await fetch(`http://localhost:8080/tasks/${id}`, {
      method: "DELETE"
    });

    tasksArray = tasksArray.filter(task => task.id != id);

    INPUT.value = "";
    delete INPUT.dataset.id;

    renderTasks(tasksArray);
  } catch (error) {
    console.error("Ошибка удаления:", error);
  }

  INPUT.focus();
});

// =======================
// 🚀 СТАРТ
// =======================
loadTasks();

// =======================
// ℹ️ ИНСТРУКЦИИ
// =======================
const overlay = document.querySelector(".overlay");
const openBtn = document.querySelector("#open-btn");
const closeBtn = document.querySelector(".close-btn");

openBtn.addEventListener("click", () => {
  overlay.style.display = "flex";
});

closeBtn.addEventListener("click", () => {
  overlay.style.display = "none";
});

overlay.addEventListener("click", (event) => {
  if (event.target === overlay) {
    overlay.style.display = "none";
  }
});