<template>
    <div class="vux-timeline">
        <ul>
            <slot></slot>
        </ul>
    </div>
</template>

<style>
.vux-timeline {
    padding: 1rem;
}

li {
    list-style: none;
}
</style>

<script>
import TimelineItem from './timeline-item.vue'

export default {
    props: {
        color: {
            type: String,
            default: '#49bdcc'
        }
    },
    components: {
        TimelineItem
    },
    ready() {
        this.setChildProps()
    },
    methods: {
        setChildProps() {
            const len = this.$children.length
            this.$children.forEach((child, index) => {
                child.isLast = index === len - 1
                child.isFirst = index === 0
            })
        }
    }
}
</script>
