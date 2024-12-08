import {ref} from 'vue'

export default function (draggable: any) {
    const dragStartPosition = {x: 0, y: 0};

    function dragStart(event: any) {  // 阻止默认的mousedown事件，防止选中文字等
        event.preventDefault();

        // 保存初始位置
        dragStartPosition.x = event.clientX - draggable.value.offsetLeft;
        dragStartPosition.y = event.clientY - draggable.value.offsetTop;

        // 添加事件监听
        document.addEventListener('mousemove', dragMove);
        document.addEventListener('mouseup', dragEnd);
        document.addEventListener('touchmove', dragMove);
        document.addEventListener('touchend', dragEnd);

    }

    function dragEnd() {
        // 移除事件监听
        document.removeEventListener('mousemove', dragMove);
        document.removeEventListener('mouseup', dragEnd);
        document.removeEventListener('touchmove', dragMove);
        document.removeEventListener('touchend', dragEnd);
    }

    function dragMove(event: any) {
        // 计算新位置
        const x = event.clientX - dragStartPosition.x;
        const y = event.clientY - dragStartPosition.y;

        // 更新位置
        draggable.value.style.left = `${x}px`;
        draggable.value.style.top = `${y}px`;
    }

    return {draggable, dragEnd, dragStart, dragMove}
}