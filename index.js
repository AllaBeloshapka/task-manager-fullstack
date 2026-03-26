// Main layout container
const MAIN = document.querySelector(".main");

// Container where tasks are rendered
const TASKS = document.querySelector(".tasks");

// Input field for creating and updating tasks
const INPUT = document.querySelector(".input");

// Input field for filtering tasks
const INPUT_FILTER = document.querySelector(".input_filter");

// Search button
const FILTER_BTN = document.querySelector("#filter-btn");

// Clear filter button
const CLEAR_BTN = document.querySelector("#clear-btn");

// Element displaying the number of active tasks
const NUMBER = document.querySelector(".number");

// Action buttons
const BTN_MINUS = document.querySelector(".btn-minus");
const BTN_PLUS = document.querySelector(".btn-plus");
const BTN_UPDATE = document.querySelector(".btn-update");

// Local state storing all tasks
let tasksArray = [];

// =======================
// Render tasks to the DOM
// =======================
function renderTasks(tasks) {
  TASKS.innerHTML = "";

  tasks.forEach((task) => {
    const taskItem = document.createElement("div");
    taskItem.classList.add("task-item");

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";

    // When checkbox is selected, fill input with task data for update or delete
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

  // Update task counter
  NUMBER.textContent = tasks.length;
}

// =======================
// Load tasks from backend
// =======================
async function loadTasks() {
  try {
    const response = await fetch("http://localhost:8080/tasks");
    const data = await response.json();

    tasksArray = data;
    renderTasks(tasksArray);
  } catch (error) {
    console.error("Error loading tasks:", error);
  }
}

// =======================
// Search functionality via backend
// =======================

// Hide clear button on initial load
CLEAR_BTN.style.display = "none";

// Clear search input and restore full task list
CLEAR_BTN.addEventListener("click", () => {
  INPUT_FILTER.value = "";
  renderTasks(tasksArray);
  CLEAR_BTN.style.display = "none";
  INPUT_FILTER.focus();
});

// Toggle clear button visibility based on input content
INPUT_FILTER.addEventListener("input", () => {
  if (INPUT_FILTER.value.trim() === "") {
    renderTasks(tasksArray);
    CLEAR_BTN.style.display = "none";
  } else {
    CLEAR_BTN.style.display = "inline-block";
  }
});

// Toast element for user notifications
const TOAST = document.getElementById("toast");

// Display temporary notification message
function showToast(message) {
  TOAST.textContent = message;
  TOAST.classList.add("show");

  setTimeout(() => {
    TOAST.classList.remove("show");
  }, 2000);
}

// Perform search request to backend
async function searchTasks() {
  const keyword = INPUT_FILTER.value.trim();

  // If input is empty, show all tasks
  if (!keyword) {
    renderTasks(tasksArray);
    return;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/tasks?keyword=${encodeURIComponent(keyword)}`
    );

    const data = await response.json();

    // If no results, clear list and notify user
    if (!Array.isArray(data) || data.length === 0) {
      renderTasks([]);
      showToast("Задача не найдена");
      return;
    }

    renderTasks(data);

  } catch (error) {
    console.error("Error during search:", error);
  }
}

// Restore full list when search input is cleared
INPUT_FILTER.addEventListener("input", () => {
  if (INPUT_FILTER.value.trim() === "") {
    renderTasks(tasksArray);
  }
});

// =======================
// Search button click
// =======================
FILTER_BTN.addEventListener("click", () => {
  searchTasks();
});

// =======================
// Trigger search on Enter key
// =======================
INPUT_FILTER.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    searchTasks();
  }
});

// =======================
// Create new task
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

    // Update local state
    tasksArray.push(newTask);

    INPUT.value = "";
    renderTasks(tasksArray);
  } catch (error) {
    console.error("Error creating task:", error);
  }

  INPUT.focus();
});

// =======================
// Update existing task
// =======================
BTN_UPDATE.addEventListener("click", async () => {
  const id = INPUT.dataset.id;
  const updatedTitle = INPUT.value.trim();

  // If no selected task or empty input, reset state
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

    // Sync local state with updated task
    const index = tasksArray.findIndex(task => task.id == id);
    if (index !== -1) {
      tasksArray[index] = updatedTask;
    }

    INPUT.value = "";
    delete INPUT.dataset.id;

    renderTasks(tasksArray);
  } catch (error) {
    console.error("Error updating task:", error);
  }

  INPUT.focus();
});

// =======================
// Delete selected task
// =======================
BTN_MINUS.addEventListener("click", async () => {
  const id = INPUT.dataset.id;

  // If no task selected, just clear input
  if (!id) {
    INPUT.value = "";
    return;
  }

  try {
    await fetch(`http://localhost:8080/tasks/${id}`, {
      method: "DELETE"
    });

    // Remove task from local state
    tasksArray = tasksArray.filter(task => task.id != id);

    INPUT.value = "";
    delete INPUT.dataset.id;

    renderTasks(tasksArray);
  } catch (error) {
    console.error("Error deleting task:", error);
  }

  INPUT.focus();
});

// =======================
// Application entry point
// =======================
loadTasks();

// =======================
// Modal instructions logic
// =======================
const overlay = document.querySelector(".overlay");
const openBtn = document.querySelector("#open-btn");
const closeBtn = document.querySelector(".close-btn");

// Open instructions modal
openBtn.addEventListener("click", () => {
  overlay.style.display = "flex";
});

// Close modal via button
closeBtn.addEventListener("click", () => {
  overlay.style.display = "none";
});

// Close modal when clicking outside content
overlay.addEventListener("click", (event) => {
  if (event.target === overlay) {
    overlay.style.display = "none";
  }
});