const MAIN = document.querySelector(".main");
const TASKS = document.querySelector(".tasks");
const INPUT = document.querySelector(".input");
const NUMBER = document.querySelector(".number");
const BTN_MINUS = document.querySelector(".btn-minus");
const BTN_PLUS = document.querySelector(".btn-plus");
const BTN_UPDATE = document.querySelector(".btn-update");


let editingTaskId = null;

// теперь тут объекты
let tasksArray = [];

// загрузка с backend
async function loadTasks() {
  try {
    const response = await fetch("http://localhost:8080/tasks");
    const data = await response.json();
    tasksArray = data;
    updateTasks();
  } catch (error) {
    console.error("Ошибка загрузки задач:", error);
  }
}

// отрисовка
function updateTasks() {
  TASKS.innerHTML = "";

  tasksArray.forEach((task) => {
    const taskItem = document.createElement("div");
    taskItem.classList.add("task-item");

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";

    checkbox.addEventListener("change", () => {
      if (checkbox.checked) {
        INPUT.value = task.title;
        INPUT.dataset.id = task.id; // сохраняем id
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

  NUMBER.textContent = tasksArray.length;
}

// ➕ добавить задачу (POST)
BTN_PLUS.addEventListener("click", async () => {
  const task = INPUT.value.trim();

  if (!task) return;

  try {
    const response = await fetch("http://localhost:8080/tasks", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        title: task
      })
    });

    const newTask = await response.json();

    tasksArray.push(newTask);

    INPUT.value = "";
    updateTasks();
  } catch (error) {
    console.error("Ошибка добавления:", error);
  }

  INPUT.focus();
});

// ✏️ обновить задачу (PUT)
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
      body: JSON.stringify({
        title: updatedTitle
      })
    });

    const updatedTask = await response.json();

    const taskIndex = tasksArray.findIndex(task => task.id == id);
    if (taskIndex !== -1) {
      tasksArray[taskIndex] = updatedTask;
    }

    INPUT.value = "";
    delete INPUT.dataset.id;

    updateTasks();
  } catch (error) {
    console.error("Ошибка обновления:", error);
  }

  INPUT.focus();
});

// ➖ удалить задачу (DELETE)
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

    updateTasks();
  } catch (error) {
    console.error("Ошибка удаления:", error);
  }

  INPUT.focus();
});

// старт
loadTasks();

// инструкции (оставляем как есть)
const overlay = document.querySelector(".overlay");
const openBtn = document.querySelector("#open-btn");
const closeBtn = document.querySelector(".close-btn");

openBtn.addEventListener('click', () => {
  overlay.style.display = 'flex';
});

closeBtn.addEventListener('click', () => {
  overlay.style.display = 'none';
});

overlay.addEventListener('click', (event) => {
  if (event.target === overlay) {
    overlay.style.display = 'none';
  }
});
